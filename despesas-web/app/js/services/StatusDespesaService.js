angular.module('gestao-despesas').factory('StatusDespesaService', ['$http', 'AppService', StatusDespesaService]);

function StatusDespesaService($http, AppService) {
	return {
		findAll: function() {
			return $http.get(AppService.HOST() + '/despesa/status', { cache: true});
		}
	}
}