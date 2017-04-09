angular.module('gestao-despesas').factory('AppService', [AppService]);

function AppService() {
	return {
		HOST: function() {
			return 'http://localhost:8081';
		}
	};
}