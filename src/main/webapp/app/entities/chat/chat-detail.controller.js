(function() {
    'use strict';

    angular
        .module('chatAnalysisApp')
        .controller('ChatDetailController', ChatDetailController);

    ChatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Chat', 'ChatSession', 'User'];

    function ChatDetailController($scope, $rootScope, $stateParams, previousState, entity, Chat, ChatSession, User) {
        var vm = this;

        vm.chat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('chatAnalysisApp:chatUpdate', function(event, result) {
            vm.chat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
