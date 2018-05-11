/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { NodeRegistryComponent } from '../../../../../../main/webapp/app/entities/node-registry/node-registry.component';
import { NodeRegistryService } from '../../../../../../main/webapp/app/entities/node-registry/node-registry.service';
import { NodeRegistry } from '../../../../../../main/webapp/app/entities/node-registry/node-registry.model';

describe('Component Tests', () => {

    describe('NodeRegistry Management Component', () => {
        let comp: NodeRegistryComponent;
        let fixture: ComponentFixture<NodeRegistryComponent>;
        let service: NodeRegistryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [NodeRegistryComponent],
                providers: [
                    NodeRegistryService
                ]
            })
            .overrideTemplate(NodeRegistryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodeRegistryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodeRegistryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new NodeRegistry(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.nodeRegistries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
