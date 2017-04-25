angular.module('gestao-despesas')
    .factory('DashBoardService', ['$http', 'AppService', DashBoardService]);

function DashBoardService($http, AppService) {
	return {
        findTotalMesAtualByCategoria: function() {
			return $http.get(AppService.HOST() + '/dashboard/total/categoria');
		},
        findTotalMesAtualByPago: function() {
            return $http.get(AppService.HOST() + '/dashboard/total/status');
        },
        findTotalAPagarMesAtual: function() {
            return $http.get(AppService.HOST() + '/dashboard/total/pagar');
        },
        findTotalPagoAtual: function() {
            return $http.get(AppService.HOST() + '/dashboard/total/pago');
        },
        findDespesasAVencerMesAtual: function() {
            return $http.get(AppService.HOST() + '/dashboard/despesas/vencer');
        },
        findDespesasAtrasadas: function() {
            return $http.get(AppService.HOST() + '/dashboard/despesas/atrasadas');
        }
	};
}