/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { NodeMetaRegistryDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/node-meta-registry/node-meta-registry-delete-dialog.component';
import { NodeMetaRegistryService } from '../../../../../../main/webapp/app/entities/node-meta-registry/node-meta-registry.service';

describe('Component Tests', () => {

    describe('NodeMetaRegistry Management Delete Component', () => {
        let comp: NodeMetaRegistryDeleteDialogComponent;
        let fixture: ComponentFixture<NodeMetaRegistryDeleteDialogComponent>;
        let service: NodeMetaRegistryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [NodeMetaRegistryDeleteDialogComponent],
                providers: [
                    NodeMetaRegistryService
                ]
            })
            .overrideTemplate(NodeMetaRegistryDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodeMetaRegistryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodeMetaRegistryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
