import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { NodesMeta } from './nodes-meta.model';
import { NodesMetaService } from './nodes-meta.service';

@Component({
    selector: 'jhi-nodes-meta-detail',
    templateUrl: './nodes-meta-detail.component.html'
})
export class NodesMetaDetailComponent implements OnInit, OnDestroy {

    nodesMeta: NodesMeta;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private nodesMetaService: NodesMetaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNodesMetas();
    }

    load(id) {
        this.nodesMetaService.find(id)
            .subscribe((nodesMetaResponse: HttpResponse<NodesMeta>) => {
                this.nodesMeta = nodesMetaResponse.body;
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

    registerChangeInNodesMetas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'nodesMetaListModification',
            (response) => this.load(this.nodesMeta.id)
        );
    }
}
