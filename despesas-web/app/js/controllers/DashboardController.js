angular.module('gestao-despesas').controller('DashboardController', ['$scope', 'DashBoardService', DashboardController]);

function DashboardController($scope, DashBoardService) {

    carregarDespesasAVencer();
    carregarDespesasAtrasadas();
    carregarTotalAPagar();
    carregarTotalPago();
    inicializarGraficoDespesaPorCategoria();
    carregarGraficoDespesasPorCategoria();
    carregarGraficoDespesasPorPago();


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
                if(response.data.length > 0) {
                    response.data.forEach(function(despesa){
                        $scope.labelsCategoria.push(despesa.descricao);
                        $scope.dataCategoria.push(despesa.valor);
                    });
                }
            }, function(error) {
                console.log('Erro ao carregar total de despesas por categoria');
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
                console.log('Erro ao carregar total de despesas por pago');
            });
    }

    function carregarDespesasAVencer() {
        DashBoardService.findDespesasAVencerMesAtual()
            .then(function(response) {
                $scope.despesasVencer = response.data;
            }, function(error) {
                console.log('Erro ao carregar despesas a vencer');
            });
    }

    function carregarTotalAPagar() {
        DashBoardService.findTotalAPagarMesAtual()
            .then(function(response) {
                $scope.totalAPagar = response.data;
            }, function(error) {
                console.log('Erro ao carregar total a pagar no mês');
            });
    }


    function carregarTotalPago() {
        DashBoardService.findTotalPagoAtual()
            .then(function (response) {
                $scope.totalPago = response.data;
            }, function (error) {
                console.log('Erro ao carregar total pago no mês');
            });
    }

    function carregarDespesasAtrasadas() {
        DashBoardService.findDespesasAtrasadas()
            .then(function(response) {
                $scope.despesasAtrasadas = response.data;
            }, function(error) {
                console.log('Erro ao carregar despesas atrasadas');
            });
    }
}