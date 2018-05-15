import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Sources } from './sources.model';
import { SourcesService } from './sources.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-sources',
    templateUrl: './sources.component.html'
})
export class SourcesComponent implements OnInit, OnDestroy {
sources: Sources[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sourcesService: SourcesService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sourcesService.query().subscribe(
            (res: HttpResponse<Sources[]>) => {
                this.sources = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSources();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Sources) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInSources() {
        this.eventSubscriber = this.eventManager.subscribe('sourcesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
