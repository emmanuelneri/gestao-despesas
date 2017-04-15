angular.module('gestao-despesas').controller('DespesasController', ['$scope', 'DespesaService', 'CategoriaService', 'StatusDespesaService', DespesasController]);

function DespesasController($scope, DespesaService, CategoriaService, StatusDespesaService) {

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
				$scope.displayedCollection = normalizarData(response.data.content);
				stState.pagination.numberOfPages = response.data.totalPages;
            	stState.pagination.totalItemCount = response.data.totalElements;
			}, function(error) {
				console.log('Erro ao filtrar despesas');
			});
    }

    function normalizarData(despesas) {
    	var despesasComDataNormalizada = [];
    	despesas.forEach(function(despesa) {
    		despesa.data = new Date(despesa.data[0], despesa.data[1]-1, despesa.data[2]);
    		despesasComDataNormalizada.push(despesa);
    	})

    	return despesasComDataNormalizada;
    }

    function getCurrentPage() {
    	var pagination = stState ? stState.pagination : {};
        return pagination.start ? Math.floor(pagination.start / $scope.search.quantidade) : 0;
    }

    $scope.getData = function (tableState) {
        if (!stState) {
            stState = tableState;
        }

        $scope.filtrar();
    }

	$scope.remover = function(despesa) {
		DespesaService.delete(despesa.id).then(function(response) {
			$scope.filtrar();
		}, function(error) {
			console.log('Erro ao remover despesa' + error);
		})
	}

	function carregarListaCategorias() {
		CategoriaService.findAll()
			.then(function(response) {
				$scope.categorias = response.data; //Carregar em cache
			}, function(error) {
				
			});
	}

	function carregarListaStatus() {
		StatusDespesaService.findAll()
			.then(function(response) {
				$scope.listaStatus = response.data; //Carregar em cache
			}, function(error) {
				console.log('Erro ao carregar categorias');
			});
	}

	function inicializar() {
		carregarListaCategorias();
		carregarListaStatus();
	}

	$scope.pagar = function(idDespesa) {
		DespesaService.pagar(idDespesa)
			.then(function(response) {
				console.log('Despesa paga com sucesso');
			}, function(error) {
				console.log('Erro ao pagar despesa' + error);
			});
	}

	inicializar();
}