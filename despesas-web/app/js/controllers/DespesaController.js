angular.module('gestao-despesas').controller('DespesaController', ['$scope', '$routeParams', 'DespesaService', 'CategoriaService', 'StatusDespesaService', DespesaController]);

function DespesaController($scope, $routeParams, DespesaService, CategoriaService, StatusDespesaService) {
	$scope.despesa = {};
	$scope.statusList = [];
	$scope.categorias = [];

	if ($routeParams.despesaId) {
		DespesaService.findById($routeParams.despesaId)
			.then(function(response) {
				var despesa = response.data;
				despesa.data = new Date(despesa.data[0], despesa.data[1]-1, despesa.data[2]);
				$scope.despesa = despesa;
			}, function(erro) {
				console.log('Erro ao buscar despesa');
			});
	}

	$scope.salvar = function() {
		DespesaService.save($scope.despesa)
			.then(function(despesa) {

			}, function(error) {
				console.log('Erro ao salvar despesa' + error);
			});
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
				$scope.statusList = response.data; //Carregar em cache
			}, function(error) {
				console.log('Erro ao carregar categorias');
			});
	}

	function inicializar() {
		carregarListaCategorias();
		carregarListaStatus();
	}

	inicializar();
}