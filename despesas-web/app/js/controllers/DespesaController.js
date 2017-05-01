angular.module('gestao-despesas').controller('DespesaController', ['$scope', '$routeParams', 'toaster', 'DespesaService', 'CategoriaService', 'StatusDespesaService', DespesaController]);

function DespesaController($scope, $routeParams, toaster, DespesaService, CategoriaService, StatusDespesaService) {

	$scope.statusList = [];
	$scope.categorias = [];

	if ($routeParams.despesaId) {
		DespesaService.findById($routeParams.despesaId)
			.then(function(response) {
				$scope.despesa = response.data;
			}, function(erro) {
				toaster.pop('error', "", "Não foi possível buscar a despesa para a edição. Contate o administrador do sistema!");
				console.error(erro);
			});
	}

	$scope.salvar = function() {
        inicializarAlerts();
		DespesaService.save($scope.despesa)
			.then(function(despesa) {
				$scope.despesa = {};

				toaster.pop('success', "", "Despesa salva com sucesso");
			}, function(error) {
				if(error.data.length) {
					error.data.forEach(function(error) {
                        $scope.alerts.push({ type: 'danger', msg: error.error});
                    });
				} else {
					toaster.pop('danger', "", "Não foi possível salvar a despesa. Contate o administrador do sistema!");
                    console.error(error);
				}
			});
	};

	function carregarListaCategorias() {
		CategoriaService.findAll()
			.then(function(response) {
				$scope.categorias = response.data;
			}, function(error) {
				console.error(error);
			});
	}

	function carregarListaStatus() {
		StatusDespesaService.findAll()
			.then(function(response) {
				$scope.statusList = response.data;
			}, function(error) {
				console.error(error);
			});
	}

	function inicializarDespesa() {
        $scope.despesa = {
            status: 'PENDENTE',
			data: new Date()
        };
    }

	function inicializar() {
		carregarListaCategorias();
		carregarListaStatus();
        inicializarDespesa();
        inicializarAlerts();
	}

	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};

    function inicializarAlerts() {
        $scope.alerts = [];
    }

	inicializar();
}