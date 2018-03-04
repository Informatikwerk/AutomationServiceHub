(function() {
    'use strict';

    angular
        .module('automationServiceHubApp')
        .controller('NodeMetaRegisterDeleteController',NodeMetaRegisterDeleteController);

    NodeMetaRegisterDeleteController.$inject = ['$uibModalInstance', 'entity', 'NodeMetaRegister'];

    function NodeMetaRegisterDeleteController($uibModalInstance, entity, NodeMetaRegister) {
        var vm = this;

        vm.nodeMetaRegister = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NodeMetaRegister.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
