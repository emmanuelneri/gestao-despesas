angular.module('gestao-despesas').directive('dateConverter', function() {
    return {
        restrict: 'A',
        require: '?ngModel',
        link: function(scope, element, attrs, ngModel) {
            ngModel.$formatters.push(function(value) {
                return (value instanceof Date) ? value : arraytoDate(value);
            });
        }
    };

    function arraytoDate(value) {
       return new Date(value[0], value[1]-1, value[2]);
    }
});