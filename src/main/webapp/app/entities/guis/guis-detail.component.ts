import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Guis } from './guis.model';
import { GuisService } from './guis.service';

@Component({
    selector: 'jhi-guis-detail',
    templateUrl: './guis-detail.component.html'
})
export class GuisDetailComponent implements OnInit, OnDestroy {

    guis: Guis;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private guisService: GuisService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGuis();
    }

    load(id) {
        this.guisService.find(id)
            .subscribe((guisResponse: HttpResponse<Guis>) => {
                this.guis = guisResponse.body;
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

    registerChangeInGuis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'guisListModification',
            (response) => this.load(this.guis.id)
        );
    }
}
