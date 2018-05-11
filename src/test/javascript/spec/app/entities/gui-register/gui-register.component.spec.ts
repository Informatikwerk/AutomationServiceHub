/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AutomationServiceHubTestModule } from '../../../test.module';
import { GuiRegisterComponent } from '../../../../../../main/webapp/app/entities/gui-register/gui-register.component';
import { GuiRegisterService } from '../../../../../../main/webapp/app/entities/gui-register/gui-register.service';
import { GuiRegister } from '../../../../../../main/webapp/app/entities/gui-register/gui-register.model';

describe('Component Tests', () => {

    describe('GuiRegister Management Component', () => {
        let comp: GuiRegisterComponent;
        let fixture: ComponentFixture<GuiRegisterComponent>;
        let service: GuiRegisterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutomationServiceHubTestModule],
                declarations: [GuiRegisterComponent],
                providers: [
                    GuiRegisterService
                ]
            })
            .overrideTemplate(GuiRegisterComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GuiRegisterComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GuiRegisterService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new GuiRegister(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.guiRegisters[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
