(function() {
    'use strict';

    angular
        .module('automationServiceHubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gui', {
            parent: 'entity',
            url: '/gui',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'automationServiceHubApp.gui.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gui/guis.html',
                    controller: 'GuiController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gui');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('gui-detail', {
            parent: 'gui',
            url: '/gui/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'automationServiceHubApp.gui.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gui/gui-detail.html',
                    controller: 'GuiDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gui');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Gui', function($stateParams, Gui) {
                    return Gui.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gui',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gui-detail.edit', {
            parent: 'gui-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gui/gui-dialog.html',
                    controller: 'GuiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gui', function(Gui) {
                            return Gui.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gui.new', {
            parent: 'gui',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gui/gui-dialog.html',
                    controller: 'GuiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                jsFile: null,
                                htmlFile: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gui', null, { reload: 'gui' });
                }, function() {
                    $state.go('gui');
                });
            }]
        })
        .state('gui.edit', {
            parent: 'gui',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gui/gui-dialog.html',
                    controller: 'GuiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gui', function(Gui) {
                            return Gui.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gui', null, { reload: 'gui' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gui.delete', {
            parent: 'gui',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gui/gui-delete-dialog.html',
                    controller: 'GuiDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Gui', function(Gui) {
                            return Gui.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gui', null, { reload: 'gui' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
