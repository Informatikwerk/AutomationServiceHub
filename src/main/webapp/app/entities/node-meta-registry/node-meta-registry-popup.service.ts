import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { NodeMetaRegistry } from './node-meta-registry.model';
import { NodeMetaRegistryService } from './node-meta-registry.service';

@Injectable()
export class NodeMetaRegistryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private nodeMetaRegistryService: NodeMetaRegistryService

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
                this.nodeMetaRegistryService.find(id)
                    .subscribe((nodeMetaRegistryResponse: HttpResponse<NodeMetaRegistry>) => {
                        const nodeMetaRegistry: NodeMetaRegistry = nodeMetaRegistryResponse.body;
                        this.ngbModalRef = this.nodeMetaRegistryModalRef(component, nodeMetaRegistry);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.nodeMetaRegistryModalRef(component, new NodeMetaRegistry());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    nodeMetaRegistryModalRef(component: Component, nodeMetaRegistry: NodeMetaRegistry): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.nodeMetaRegistry = nodeMetaRegistry;
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
