import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NodeRegistry } from './node-registry.model';
import { NodeRegistryService } from './node-registry.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-node-registry',
    templateUrl: './node-registry.component.html'
})
export class NodeRegistryComponent implements OnInit, OnDestroy {
nodeRegistries: NodeRegistry[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private nodeRegistryService: NodeRegistryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.nodeRegistryService.query().subscribe(
            (res: HttpResponse<NodeRegistry[]>) => {
                this.nodeRegistries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNodeRegistries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: NodeRegistry) {
        return item.id;
    }
    registerChangeInNodeRegistries() {
        this.eventSubscriber = this.eventManager.subscribe('nodeRegistryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
