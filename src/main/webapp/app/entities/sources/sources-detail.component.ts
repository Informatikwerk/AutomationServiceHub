import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Sources } from './sources.model';
import { SourcesService } from './sources.service';

@Component({
    selector: 'jhi-sources-detail',
    templateUrl: './sources-detail.component.html'
})
export class SourcesDetailComponent implements OnInit, OnDestroy {

    sources: Sources;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private sourcesService: SourcesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSources();
    }

    load(id) {
        this.sourcesService.find(id)
            .subscribe((sourcesResponse: HttpResponse<Sources>) => {
                this.sources = sourcesResponse.body;
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

    registerChangeInSources() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sourcesListModification',
            (response) => this.load(this.sources.id)
        );
    }
}
