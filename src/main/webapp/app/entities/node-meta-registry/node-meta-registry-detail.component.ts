import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { NodeMetaRegistry } from './node-meta-registry.model';
import { NodeMetaRegistryService } from './node-meta-registry.service';

@Component({
    selector: 'jhi-node-meta-registry-detail',
    templateUrl: './node-meta-registry-detail.component.html'
})
export class NodeMetaRegistryDetailComponent implements OnInit, OnDestroy {

    nodeMetaRegistry: NodeMetaRegistry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private nodeMetaRegistryService: NodeMetaRegistryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNodeMetaRegistries();
    }

    load(id) {
        this.nodeMetaRegistryService.find(id)
            .subscribe((nodeMetaRegistryResponse: HttpResponse<NodeMetaRegistry>) => {
                this.nodeMetaRegistry = nodeMetaRegistryResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNodeMetaRegistries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'nodeMetaRegistryListModification',
            (response) => this.load(this.nodeMetaRegistry.id)
        );
    }
}
