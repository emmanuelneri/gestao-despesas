package br.com.despesas.exception;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static final String SISTEMA_INDISPONIVEL = "O sistema se encontra indisponível";
    private static final String SQL_VIOLATION_CODE_UNIQUE_CONSTRAINT = "23505";

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Set<ExceptionVO>> handleError(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singleton(new ExceptionVO(ex.getMessage())));
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<Set<ExceptionVO>> handleError(TransactionSystemException tse) {
        if (tse.getCause() != null && tse.getCause() instanceof RollbackException) {
            final RollbackException re = (RollbackException) tse.getCause();

            if (re.getCause() != null && re.getCause() instanceof ConstraintViolationException) {
                return handleError((ConstraintViolationException) re.getCause());
            }
        }
        throw tse;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Set<ExceptionVO>> handleError(DataIntegrityViolationException divex) {
        boolean isUniqueConstraintError = Throwables.getCausalChain(divex).stream().anyMatch(ex -> {
            if (ex instanceof org.hibernate.exception.ConstraintViolationException) {
                org.hibernate.exception.ConstraintViolationException cvex = (org.hibernate.exception.ConstraintViolationException) ex;
                if (cvex.getSQLState().equals(SQL_VIOLATION_CODE_UNIQUE_CONSTRAINT)) {
                    return true;
                }
            }
            return false;
        });

        return isUniqueConstraintError
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singleton(
                        new ExceptionVO("Esse registro já existe na base de dados.")))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singleton(new ExceptionVO(divex.getMessage())));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Set<ExceptionVO>> handleError(ConstraintViolationException ex) {
        final Set<ExceptionVO> erros = ex.getConstraintViolations().stream().map(c -> new ExceptionVO(c.getMessage())).collect(Collectors.toSet());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleError(Exception ex) {
        log.error("Internal error server", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SISTEMA_INDISPONIVEL);
    }
}
