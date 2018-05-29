import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Nodes } from './nodes.model';
import { NodesService } from './nodes.service';

@Component({
    selector: 'jhi-nodes-detail',
    templateUrl: './nodes-detail.component.html'
})
export class NodesDetailComponent implements OnInit, OnDestroy {

    nodes: Nodes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private nodesService: NodesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNodes();
    }

    load(id) {
        this.nodesService.find(id)
            .subscribe((nodesResponse: HttpResponse<Nodes>) => {
                this.nodes = nodesResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNodes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'nodesListModification',
            (response) => this.load(this.nodes.id)
        );
    }
}
