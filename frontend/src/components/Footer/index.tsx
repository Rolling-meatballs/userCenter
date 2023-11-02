// import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import React from 'react';
import {PLATFORM_URL} from "@/constants";

const Footer: React.FC = () => {
  const intl = useIntl();
  const defaultMessage = intl.formatMessage({
    id: 'app.copyright.produced',
    defaultMessage: '平行时空工作室',
  });

  // const currentYear = new Date().getFullYear();
  const currentYear = 2023;

  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'workspace',
          title: '平行时空',
          href: 'https://pro.ant.design',
          blankTarget: true,
        },
        {
          key: 'bilibili',
          title: '平行时空的音乐之旅',
          href: PLATFORM_URL,
          blankTarget: true,
        },
        {
          key: 'parallelDuo',
          title: 'parallel Duo',
          href: 'https://ant.design',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
