import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Nodes } from './nodes.model';
import { NodesService } from './nodes.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-nodes',
    templateUrl: './nodes.component.html'
})
export class NodesComponent implements OnInit, OnDestroy {
nodes: Nodes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private nodesService: NodesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.nodesService.query().subscribe(
            (res: HttpResponse<Nodes[]>) => {
                this.nodes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNodes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Nodes) {
        return item.id;
    }
    registerChangeInNodes() {
        this.eventSubscriber = this.eventManager.subscribe('nodesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
