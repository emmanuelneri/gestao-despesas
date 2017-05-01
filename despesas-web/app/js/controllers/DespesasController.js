angular.module('gestao-despesas').controller('DespesasController', ['$scope', '$location', 'toaster', 'DespesaService', 'CategoriaService', 'StatusDespesaService', DespesasController]);

function DespesasController($scope, $location, toaster, DespesaService, CategoriaService, StatusDespesaService) {

	var stState;

	$scope.search = {
        pagina: 0,
        quantidade: 10,
        paga: null
    };

	$scope.filtrar = function() {
        $scope.search.pagina = getCurrentPage();

        DespesaService.search($scope.search)
			.then(function(response) {
				$scope.displayedCollection = response.data.content;
				stState.pagination.numberOfPages = response.data.totalPages;
            	stState.pagination.totalItemCount = response.data.totalElements;
			}, function(error) {
				console.error(error);
			});
    };

    function getCurrentPage() {
    	var pagination = stState ? stState.pagination : {};
        return pagination.start ? Math.floor(pagination.start / $scope.search.quantidade) : 0;
    }

    $scope.getData = function (tableState) {
        if (!stState) {
            stState = tableState;
        }

        $scope.filtrar();
    };

	$scope.remover = function(despesa) {
		DespesaService.delete(despesa.id).then(function(response) {
			toaster.pop('success', "", "Despesa excluída com sucesso");
			$scope.filtrar();
		}, function(error) {
			console.error(error);
		});
	};

	$scope.editar = function (despesa) {
        $location.path("/despesa/" + despesa.id);
    };

	function carregarListaCategorias() {
		CategoriaService.findAll()
			.then(function(response) {
				$scope.categorias = response.data;
			}, function(error) {
				
			});
	}

	function carregarListaStatus() {
		StatusDespesaService.findAll()
			.then(function(response) {
				$scope.listaStatus = response.data;
			}, function(error) {
				console.error(error);
			});
	}

	function inicializar() {
		carregarListaCategorias();
		carregarListaStatus();
	}

	$scope.pagar = function(idDespesa) {
		DespesaService.pagar(idDespesa)
			.then(function(response) {
				toaster.pop('success', "", "Despesa paga com sucesso");
			}, function(error) {
				console.error(error);
				toaster.pop('error', "", "Ocorreu um erro ao pagar a despesa. Contate o administrador do sistema!");
			});
	};

	inicializar();
}