(function() {
    'use strict';

    angular
        .module('automationServiceHubApp')
        .controller('GuiDialogController', GuiDialogController);

    GuiDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Gui'];

    function GuiDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Gui) {
        var vm = this;

        vm.gui = entity;
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
            if (vm.gui.id !== null) {
                Gui.update(vm.gui, onSaveSuccess, onSaveError);
            } else {
                Gui.save(vm.gui, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('automationServiceHubApp:guiUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
