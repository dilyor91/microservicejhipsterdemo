import { IAddmissionRules, NewAddmissionRules } from './addmission-rules.model';

export const sampleWithRequiredData: IAddmissionRules = {
  id: 94723,
  titleUz: 'Devolved',
  titleRu: 'application Borders indexing',
  titleKr: 'neural',
  contentUz: 'primary programming',
  contentRu: 'Djibouti IB',
  contentKr: 'bypassing Ohio Configurable',
  status: false,
};

export const sampleWithPartialData: IAddmissionRules = {
  id: 11584,
  titleUz: 'invoice',
  titleRu: 'Handmade withdrawal Pakistan',
  titleKr: 'Cambridgeshire paradigm',
  contentUz: 'Car Afghani Compatible',
  contentRu: 'applications transmit',
  contentKr: 'withdrawal input Director',
  status: false,
};

export const sampleWithFullData: IAddmissionRules = {
  id: 73415,
  titleUz: 'Dinar Colombian',
  titleRu: 'Alabama Security',
  titleKr: 'black Neck',
  contentUz: 'architectures Dollar haptic',
  contentRu: 'Switchable Gourde',
  contentKr: 'drive pixel green',
  status: false,
};

export const sampleWithNewData: NewAddmissionRules = {
  titleUz: 'invoice Dynamic',
  titleRu: 'Kwanza',
  titleKr: 'Buckinghamshire Specialist',
  contentUz: 'wireless Pula',
  contentRu: 'parsing Algerian',
  contentKr: 'Ergonomic',
  status: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
