import { IRegInstructuion, NewRegInstructuion } from './reg-instructuion.model';

export const sampleWithRequiredData: IRegInstructuion = {
  id: 69225,
  titleUz: 'stable input',
  titleRu: 'Bermudian',
  titleKr: 'Consultant haptic scalable',
  contentUz: 'Fully-configurable',
  contentRu: 'invoice',
  contentKr: 'Quality',
  status: true,
};

export const sampleWithPartialData: IRegInstructuion = {
  id: 24467,
  titleUz: 'Electronics',
  titleRu: 'Regional Realigned hub',
  titleKr: 'lime Pennsylvania',
  contentUz: 'cohesive analyzing invoice',
  contentRu: 'parsing maximized harness',
  contentKr: 'withdrawal Pizza',
  status: true,
};

export const sampleWithFullData: IRegInstructuion = {
  id: 14638,
  titleUz: 'Salad Home',
  titleRu: 'withdrawal Handcrafted International',
  titleKr: 'synthesize generating Sausages',
  contentUz: 'Mount Liaison',
  contentRu: 'Heard synthesizing Practical',
  contentKr: 'Producer Nigeria artificial',
  status: false,
};

export const sampleWithNewData: NewRegInstructuion = {
  titleUz: 'Bedfordshire Armenian',
  titleRu: 'Ergonomic Account',
  titleKr: 'Rustic Buckinghamshire',
  contentUz: 'teal SSL Organized',
  contentRu: 'Mall Awesome Knolls',
  contentKr: 'Human Card Rupee',
  status: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
