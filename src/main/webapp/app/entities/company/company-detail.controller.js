(function() {
    'use strict';

    angular
        .module('chatAnalysisApp')
        .controller('CompanyDetailController', CompanyDetailController);

    CompanyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Company', 'User'];

    function CompanyDetailController($scope, $rootScope, $stateParams, previousState, entity, Company, User) {
        var vm = this;

        vm.company = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('chatAnalysisApp:companyUpdate', function(event, result) {
            vm.company = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
