(function() {
    'use strict';

    angular
        .module('automationServiceHubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('node-registry', {
            parent: 'entity',
            url: '/node-registry',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'automationServiceHubApp.nodeRegistry.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/node-registry/node-registries.html',
                    controller: 'NodeRegistryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('nodeRegistry');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('node-registry-detail', {
            parent: 'node-registry',
            url: '/node-registry/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'automationServiceHubApp.nodeRegistry.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/node-registry/node-registry-detail.html',
                    controller: 'NodeRegistryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('nodeRegistry');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'NodeRegistry', function($stateParams, NodeRegistry) {
                    return NodeRegistry.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'node-registry',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('node-registry-detail.edit', {
            parent: 'node-registry-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/node-registry/node-registry-dialog.html',
                    controller: 'NodeRegistryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NodeRegistry', function(NodeRegistry) {
                            return NodeRegistry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('node-registry.new', {
            parent: 'node-registry',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/node-registry/node-registry-dialog.html',
                    controller: 'NodeRegistryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ip: null,
                                nodeId: null,
                                realmKey: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('node-registry', null, { reload: 'node-registry' });
                }, function() {
                    $state.go('node-registry');
                });
            }]
        })
        .state('node-registry.edit', {
            parent: 'node-registry',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/node-registry/node-registry-dialog.html',
                    controller: 'NodeRegistryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NodeRegistry', function(NodeRegistry) {
                            return NodeRegistry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('node-registry', null, { reload: 'node-registry' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('node-registry.delete', {
            parent: 'node-registry',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/node-registry/node-registry-delete-dialog.html',
                    controller: 'NodeRegistryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NodeRegistry', function(NodeRegistry) {
                            return NodeRegistry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('node-registry', null, { reload: 'node-registry' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
