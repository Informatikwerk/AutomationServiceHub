(function() {
    'use strict';

    angular
        .module('automationServiceHubApp')
        .controller('GuiDeleteController',GuiDeleteController);

    GuiDeleteController.$inject = ['$uibModalInstance', 'entity', 'Gui'];

    function GuiDeleteController($uibModalInstance, entity, Gui) {
        var vm = this;

        vm.gui = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Gui.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
