import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { NodeRegistry } from './node-registry.model';
import { NodeRegistryService } from './node-registry.service';

@Component({
    selector: 'jhi-node-registry-detail',
    templateUrl: './node-registry-detail.component.html'
})
export class NodeRegistryDetailComponent implements OnInit, OnDestroy {

    nodeRegistry: NodeRegistry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private nodeRegistryService: NodeRegistryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNodeRegistries();
    }

    load(id) {
        this.nodeRegistryService.find(id)
            .subscribe((nodeRegistryResponse: HttpResponse<NodeRegistry>) => {
                this.nodeRegistry = nodeRegistryResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNodeRegistries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'nodeRegistryListModification',
            (response) => this.load(this.nodeRegistry.id)
        );
    }
}
