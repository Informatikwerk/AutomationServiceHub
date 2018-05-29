/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { NodesDetailComponent } from '../../../../../../main/webapp/app/entities/nodes/nodes-detail.component';
import { NodesService } from '../../../../../../main/webapp/app/entities/nodes/nodes.service';
import { Nodes } from '../../../../../../main/webapp/app/entities/nodes/nodes.model';

describe('Component Tests', () => {

    describe('Nodes Management Detail Component', () => {
        let comp: NodesDetailComponent;
        let fixture: ComponentFixture<NodesDetailComponent>;
        let service: NodesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [NodesDetailComponent],
                providers: [
                    NodesService
                ]
            })
            .overrideTemplate(NodesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Nodes(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.nodes).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
