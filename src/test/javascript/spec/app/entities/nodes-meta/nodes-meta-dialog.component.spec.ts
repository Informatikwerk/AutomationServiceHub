/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { NodesMetaDialogComponent } from '../../../../../../main/webapp/app/entities/nodes-meta/nodes-meta-dialog.component';
import { NodesMetaService } from '../../../../../../main/webapp/app/entities/nodes-meta/nodes-meta.service';
import { NodesMeta } from '../../../../../../main/webapp/app/entities/nodes-meta/nodes-meta.model';

describe('Component Tests', () => {

    describe('NodesMeta Management Dialog Component', () => {
        let comp: NodesMetaDialogComponent;
        let fixture: ComponentFixture<NodesMetaDialogComponent>;
        let service: NodesMetaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [NodesMetaDialogComponent],
                providers: [
                    NodesMetaService
                ]
            })
            .overrideTemplate(NodesMetaDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodesMetaDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodesMetaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NodesMeta(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.nodesMeta = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'nodesMetaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NodesMeta();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.nodesMeta = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'nodesMetaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
