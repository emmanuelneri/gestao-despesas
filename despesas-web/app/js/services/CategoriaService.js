angular.module('gestao-despesas')
    .factory('CategoriaService', ['$http', 'AppService', CategoriaService]);

function CategoriaService($http, AppService) {
	return {
		findAll: function() {
			return $http.get(AppService.HOST() + '/categorias', { cache: true});
		}
	}
}