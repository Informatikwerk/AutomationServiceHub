(function() {
    'use strict';

    angular
        .module('automationServiceHubApp')
        .controller('NodeMetaRegisterDialogController', NodeMetaRegisterDialogController);

    NodeMetaRegisterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'NodeMetaRegister'];

    function NodeMetaRegisterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, NodeMetaRegister) {
        var vm = this;

        vm.nodeMetaRegister = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.nodeMetaRegister.id !== null) {
                NodeMetaRegister.update(vm.nodeMetaRegister, onSaveSuccess, onSaveError);
            } else {
                NodeMetaRegister.save(vm.nodeMetaRegister, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('automationServiceHubApp:nodeMetaRegisterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
