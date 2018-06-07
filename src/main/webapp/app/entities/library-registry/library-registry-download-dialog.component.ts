import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { saveAs } from 'file-saver/FileSaver';
import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { LibraryRegistry } from './library-registry.model';
import { LibraryRegistryPopupService } from './library-registry-popup.service';
import { Principal, User } from '../../shared';
import { RealmKeyGeneratorService } from './realm-key-generator.service';
import { SourcesService } from '../sources';

@Component({
    selector: 'jhi-library-registry-download-dialog',
    templateUrl: './library-registry-download-dialog.component.html'
})
export class LibraryRegistryDownloadDialogComponent implements OnInit {

    libraryRegistry: LibraryRegistry;
    realmKey: string;
    downloadEnabled: boolean;
    currentAccount: any;

    constructor(
        public activeModal: NgbActiveModal,
        private principal: Principal,
        private sourcesService: SourcesService,
        private realmKeyGeneratorService: RealmKeyGeneratorService
    ) {
    }

    ngOnInit() {
        this.downloadEnabled = false;
        this.principal.identity().then((account: User) => {
            if (account != null) {
                this.currentAccount = account;
            }
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    copyRealmKey() {
        console.log('Copied!');
        let selBox = document.createElement('textarea');
        selBox.style.position = 'fixed';
        selBox.style.left = '0';
        selBox.style.top = '0';
        selBox.style.opacity = '0';
        selBox.value = this.realmKey;
        document.body.appendChild(selBox);
        selBox.focus();
        selBox.select();
        document.execCommand('copy');
        document.body.removeChild(selBox);
    }

    download() {
        this.sourcesService.getZip(this.libraryRegistry.id, this.realmKey).subscribe(res => {
            console.log('blob ', res);
            saveAs(res, this.libraryRegistry.name + '.zip');
        });
    }

    generateRealmKey() {
        this.subscribeToResponse(
            this.realmKeyGeneratorService.get());
    }

    private subscribeToResponse(result: Observable<string>) {
        result.subscribe(result => this.onSuccess(result));
    }

    private onSuccess(result) {
        this.realmKey = result;
        this.downloadEnabled = false;
    }
}

@Component({
    selector: 'jhi-library-registry-download-popup',
    template: ''
})
export class LibraryRegistryDownloadPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private libraryRegistryPopupService: LibraryRegistryPopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.libraryRegistryPopupService
                    .open(LibraryRegistryDownloadDialogComponent as Component, params['id']);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
