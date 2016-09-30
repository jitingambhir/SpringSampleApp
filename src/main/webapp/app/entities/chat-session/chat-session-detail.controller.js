(function() {
    'use strict';

    angular
        .module('chatAnalysisApp')
        .controller('ChatSessionDetailController', ChatSessionDetailController);

    ChatSessionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ChatSession', 'Chat'];

    function ChatSessionDetailController($scope, $rootScope, $stateParams, previousState, entity, ChatSession, Chat) {
        var vm = this;

        vm.chatSession = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('chatAnalysisApp:chatSessionUpdate', function(event, result) {
            vm.chatSession = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
