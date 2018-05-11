import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { NodeMetaRegistry } from './node-meta-registry.model';
import { NodeMetaRegistryService } from './node-meta-registry.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-node-meta-registry',
    templateUrl: './node-meta-registry.component.html'
})
export class NodeMetaRegistryComponent implements OnInit, OnDestroy {
nodeMetaRegistries: NodeMetaRegistry[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private nodeMetaRegistryService: NodeMetaRegistryService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.nodeMetaRegistryService.query().subscribe(
            (res: HttpResponse<NodeMetaRegistry[]>) => {
                this.nodeMetaRegistries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNodeMetaRegistries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: NodeMetaRegistry) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInNodeMetaRegistries() {
        this.eventSubscriber = this.eventManager.subscribe('nodeMetaRegistryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
