(function() {
    'use strict';

    angular
        .module('espBrokerApp')
        .controller('NodeRegistryDialogController', NodeRegistryDialogController);

    NodeRegistryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'NodeRegistry'];

    function NodeRegistryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, NodeRegistry) {
        var vm = this;

        vm.nodeRegistry = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.nodeRegistry.id !== null) {
                NodeRegistry.update(vm.nodeRegistry, onSaveSuccess, onSaveError);
            } else {
                NodeRegistry.save(vm.nodeRegistry, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('espBrokerApp:nodeRegistryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
