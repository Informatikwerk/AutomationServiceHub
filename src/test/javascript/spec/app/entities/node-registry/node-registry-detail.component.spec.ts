/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { NodeRegistryDetailComponent } from '../../../../../../main/webapp/app/entities/node-registry/node-registry-detail.component';
import { NodeRegistryService } from '../../../../../../main/webapp/app/entities/node-registry/node-registry.service';
import { NodeRegistry } from '../../../../../../main/webapp/app/entities/node-registry/node-registry.model';

describe('Component Tests', () => {

    describe('NodeRegistry Management Detail Component', () => {
        let comp: NodeRegistryDetailComponent;
        let fixture: ComponentFixture<NodeRegistryDetailComponent>;
        let service: NodeRegistryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [NodeRegistryDetailComponent],
                providers: [
                    NodeRegistryService
                ]
            })
            .overrideTemplate(NodeRegistryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodeRegistryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodeRegistryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new NodeRegistry(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.nodeRegistry).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
