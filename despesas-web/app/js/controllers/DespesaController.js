angular.module('gestao-despesas').controller('DespesaController', ['$scope', '$routeParams', 'DespesaService', 'CategoriaService', 'StatusDespesaService', DespesaController]);

function DespesaController($scope, $routeParams, DespesaService, CategoriaService, StatusDespesaService) {

	$scope.statusList = [];
	$scope.categorias = [];

	if ($routeParams.despesaId) {
		DespesaService.findById($routeParams.despesaId)
			.then(function(response) {
				var despesa = response.data;
				despesa.data = new Date(despesa.data[0], despesa.data[1]-1, despesa.data[2]);
				$scope.despesa = despesa;
			}, function(erro) {
				$scope.alerts.push({
					type: 'danger',
					msg: 'Não foi possível buscar a despesa para a edição. Contate o administrador do sistema!'
				})
				console.log('Erro ao buscar despesa: ' + error);
			});
	}

	$scope.salvar = function() {
        inicializarAlerts();
		DespesaService.save($scope.despesa)
			.then(function(despesa) {
				$scope.despesa = {};
				$scope.alerts.push({
					type: 'success',
					msg: 'Despesa salva com sucesso!'
				})
			}, function(response) {
				if(response.data.length) {
                    response.data.forEach(function(error) {
                        $scope.alerts.push({ type: 'danger', msg: error.error});
                    });
				} else {
                    $scope.alerts.push({
                        type: 'danger',
                        msg: 'Não foi possível salvar a despesa. Contate o administrador do sistema!'
                    });
                    console.log('Erro ao salvar despesa:' + response);
				}
			});
	};

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
				$scope.statusList = response.data; //Carregar em cache
			}, function(error) {
				console.log('Erro ao carregar categorias');
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
	}

    function inicializarAlerts() {
        $scope.alerts = [];
    };

	inicializar();
}