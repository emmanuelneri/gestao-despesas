angular.module('gestao-despesas')
    .factory('DespesaService', ['$http', 'AppService', DespesaService]);

function DespesaService($http, AppService) {

    return {
        save : function(despesa) {
            return $http.post(AppService.HOST() + '/despesas', despesa);
        },

        findById : function(id) {
            return $http.get(AppService.HOST() + '/despesas/' + id);
        },

        findAll: function() {
            return $http.get(AppService.HOST() + '/despesas');
        },

        delete: function(id) {
            return $http.delete(AppService.HOST() + '/despesas/' + id);
        },

        search: function(search) {
            return $http.post(AppService.HOST() + '/despesas/search', search);
        }
    }
}