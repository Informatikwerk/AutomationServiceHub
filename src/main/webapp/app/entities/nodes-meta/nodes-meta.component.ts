import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { NodesMeta } from './nodes-meta.model';
import { NodesMetaService } from './nodes-meta.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-nodes-meta',
    templateUrl: './nodes-meta.component.html'
})
export class NodesMetaComponent implements OnInit, OnDestroy {
nodesMetas: NodesMeta[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private nodesMetaService: NodesMetaService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.nodesMetaService.query().subscribe(
            (res: HttpResponse<NodesMeta[]>) => {
                this.nodesMetas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNodesMetas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: NodesMeta) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInNodesMetas() {
        this.eventSubscriber = this.eventManager.subscribe('nodesMetaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
