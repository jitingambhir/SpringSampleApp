(function() {
    'use strict';

    angular
        .module('chatAnalysisApp')
        .controller('ChatSessionDeleteController',ChatSessionDeleteController);

    ChatSessionDeleteController.$inject = ['$uibModalInstance', 'entity', 'ChatSession'];

    function ChatSessionDeleteController($uibModalInstance, entity, ChatSession) {
        var vm = this;

        vm.chatSession = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ChatSession.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
