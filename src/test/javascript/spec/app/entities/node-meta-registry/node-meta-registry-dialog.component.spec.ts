/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { NodeMetaRegistryDialogComponent } from '../../../../../../main/webapp/app/entities/node-meta-registry/node-meta-registry-dialog.component';
import { NodeMetaRegistryService } from '../../../../../../main/webapp/app/entities/node-meta-registry/node-meta-registry.service';
import { NodeMetaRegistry } from '../../../../../../main/webapp/app/entities/node-meta-registry/node-meta-registry.model';

describe('Component Tests', () => {

    describe('NodeMetaRegistry Management Dialog Component', () => {
        let comp: NodeMetaRegistryDialogComponent;
        let fixture: ComponentFixture<NodeMetaRegistryDialogComponent>;
        let service: NodeMetaRegistryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [NodeMetaRegistryDialogComponent],
                providers: [
                    NodeMetaRegistryService
                ]
            })
            .overrideTemplate(NodeMetaRegistryDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodeMetaRegistryDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodeMetaRegistryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NodeMetaRegistry(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.nodeMetaRegistry = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'nodeMetaRegistryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NodeMetaRegistry();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.nodeMetaRegistry = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'nodeMetaRegistryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
