(function() {
    'use strict';

    angular
        .module('espBrokerApp')
        .controller('NodeRegistryDeleteController',NodeRegistryDeleteController);

    NodeRegistryDeleteController.$inject = ['$uibModalInstance', 'entity', 'NodeRegistry'];

    function NodeRegistryDeleteController($uibModalInstance, entity, NodeRegistry) {
        var vm = this;

        vm.nodeRegistry = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NodeRegistry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
