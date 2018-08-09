/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { NodesMetaDetailComponent } from '../../../../../../main/webapp/app/entities/nodes-meta/nodes-meta-detail.component';
import { NodesMetaService } from '../../../../../../main/webapp/app/entities/nodes-meta/nodes-meta.service';
import { NodesMeta } from '../../../../../../main/webapp/app/entities/nodes-meta/nodes-meta.model';

describe('Component Tests', () => {

    describe('NodesMeta Management Detail Component', () => {
        let comp: NodesMetaDetailComponent;
        let fixture: ComponentFixture<NodesMetaDetailComponent>;
        let service: NodesMetaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [NodesMetaDetailComponent],
                providers: [
                    NodesMetaService
                ]
            })
            .overrideTemplate(NodesMetaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodesMetaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodesMetaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new NodesMeta(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.nodesMeta).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
