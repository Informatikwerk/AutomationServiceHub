import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { NodeRegistry } from './node-registry.model';
import { NodeRegistryService } from './node-registry.service';

@Injectable()
export class NodeRegistryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private nodeRegistryService: NodeRegistryService

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
                this.nodeRegistryService.find(id)
                    .subscribe((nodeRegistryResponse: HttpResponse<NodeRegistry>) => {
                        const nodeRegistry: NodeRegistry = nodeRegistryResponse.body;
                        this.ngbModalRef = this.nodeRegistryModalRef(component, nodeRegistry);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.nodeRegistryModalRef(component, new NodeRegistry());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    nodeRegistryModalRef(component: Component, nodeRegistry: NodeRegistry): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.nodeRegistry = nodeRegistry;
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
