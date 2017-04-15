angular.module('gestao-despesas', ['ngRoute', 'ngResource', 'ng-currency', 'ui.mask', 'smart-table', 'ui.bootstrap', 'ui.toggle'])
	.config(function($routeProvider, $locationProvider) {
		$locationProvider.hashPrefix('');

		$routeProvider
		.when('/despesas', {
			templateUrl: 'html/despesas.html',
			controller: 'DespesasController'
		})
		.when('/despesa/:despesaId', {
			templateUrl: 'html/despesa.html',
			controller: 'DespesaController'
		})
		.when('/despesa', {
			templateUrl: 'html/despesa.html',
			controller: 'DespesaController'
		})
		.otherwise({redirectTo: '/despesas'});
	});