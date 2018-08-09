/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { NodesComponent } from '../../../../../../main/webapp/app/entities/nodes/nodes.component';
import { NodesService } from '../../../../../../main/webapp/app/entities/nodes/nodes.service';
import { Nodes } from '../../../../../../main/webapp/app/entities/nodes/nodes.model';

describe('Component Tests', () => {

    describe('Nodes Management Component', () => {
        let comp: NodesComponent;
        let fixture: ComponentFixture<NodesComponent>;
        let service: NodesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [NodesComponent],
                providers: [
                    NodesService
                ]
            })
            .overrideTemplate(NodesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Nodes(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.nodes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
