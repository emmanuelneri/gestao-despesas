angular.module('gestao-despesas').controller('DashboardController', ['$scope', 'DashBoardService', DashboardController]);

function DashboardController($scope, DashBoardService) {

    function inicializarGraficoDespesaPorCategoria() {
        $scope.graficoOptions = {legend: {display: true}};

        $scope.labelsCategoria = [];
        $scope.dataCategoria = [];

        $scope.labelsPago = [];
        $scope.dataPago = [];
    }

    function carregarGraficoDespesasPorCategoria() {
        DashBoardService.findTotalMesAtualByCategoria()
            .then(function(response) {
                if(response.data.length) {
                    response.data.forEach(function(despesa){
                        $scope.labelsCategoria.push(despesa.descricao);
                        $scope.dataCategoria.push(despesa.valor);
                    });
                }
            }, function(error) {
                console.error('Erro ao carregar total de despesas por categoria: ' + error);
            });
    }

    function carregarGraficoDespesasPorPago() {
        DashBoardService.findTotalMesAtualByPago()
            .then(function(response) {
                if(response.data.length > 0) {
                    response.data.forEach(function(despesa){
                        $scope.labelsPago.push(despesa.descricao);
                        $scope.dataPago.push(despesa.valor);
                    });
                }
            }, function(error) {
                console.error('Erro ao carregar total de despesas por pago: ' + error);
            });
    }

    function carregarDespesasAVencer() {
        DashBoardService.findDespesasAVencerMesAtual()
            .then(function(response) {
                $scope.despesasVencer = response.data;
            }, function(error) {
                console.error('Erro ao carregar despesas a vencer: ' + error);
            });
    }

    function carregarTotalAPagar() {
        DashBoardService.findTotalAPagarMesAtual()
            .then(function(response) {
                $scope.totalAPagar = response.data;
            }, function(error) {
                console.error('Erro ao carregar total a pagar no mês: ' + error);
            });
    }


    function carregarTotalPago() {
        DashBoardService.findTotalPagoAtual()
            .then(function (response) {
                $scope.totalPago = response.data;
            }, function (error) {
                console.error('Erro ao carregar total pago no mês: ' + error);
            });
    }

    function carregarDespesasAtrasadas() {
        DashBoardService.findDespesasAtrasadas()
            .then(function(response) {
                $scope.despesasAtrasadas = response.data;
            }, function(error) {
                console.error('Erro ao carregar despesas atrasadas: ' + error);
            });
    }
    
    function inicializar() {
        carregarDespesasAVencer();
        carregarDespesasAtrasadas();
        carregarTotalAPagar();
        carregarTotalPago();
        inicializarGraficoDespesaPorCategoria();
        carregarGraficoDespesasPorCategoria();
        carregarGraficoDespesasPorPago();
    }

    inicializar();
}