import { MenuItem } from './menu.model';

export const MENU: MenuItem[] = [
    {
        label: 'Tableau de bord',
        icon: 'home',
        link: '/'
    },
    {
        label: 'Analyse',
        icon: 'activity',
        subItems: [
            {
                label: 'Le journal de prévision',
                link: '/apps/journal-prev',
            }
        ]
    },
    {
        label: 'Dêttes',
        icon: 'trending-down',
        link: '/debt'
    },
    {
        label: 'Comptabilité',
        icon: 'book',
        subItems: [
            {
                label: 'Les comptes',
                subItems: [
                    {
                        label: 'Les comptes de réferences',
                        link: '/apps/comptes-ref',
                    },
                    {
                        label: 'Les sous comptes',
                        link: '/apps/comptes-sub',
                    }
                ]
            },
            {
                label: 'Exercices',
                link: '/apps/fiscal-year',
            },
            {
                label: 'Le journal',
                link: '/apps/journal',
            },
            {
                label: 'Le grand livre',
                link: '/apps/ledger',
            },
            {
                label: 'La balance',
                link: '/apps/balance',
            },
            {
                label: 'Le resultat',
                link: '/apps/resultat',
            },
            {
                label: 'Le bilan',
                link: '/apps/bilan',
            },
            {
                label: 'Les graphs',
                link: '/apps/graphique',
            }
        ]
    },
    {
        label: 'Espace de Travail',
        icon: 'layers',
        link: '/ns'
    }
];
