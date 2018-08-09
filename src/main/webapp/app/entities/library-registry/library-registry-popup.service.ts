import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { LibraryRegistry } from './library-registry.model';
import { LibraryRegistryService } from './library-registry.service';

@Injectable()
export class LibraryRegistryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private libraryRegistryService: LibraryRegistryService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.libraryRegistryService.find(id)
                    .subscribe((libraryRegistryResponse: HttpResponse<LibraryRegistry>) => {
                        const libraryRegistry: LibraryRegistry = libraryRegistryResponse.body;
                        this.ngbModalRef = this.libraryRegistryModalRef(component, libraryRegistry);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.libraryRegistryModalRef(component, new LibraryRegistry());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    libraryRegistryModalRef(component: Component, libraryRegistry: LibraryRegistry): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.libraryRegistry = libraryRegistry;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
