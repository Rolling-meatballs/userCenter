import { HeartTwoTone, SmileTwoTone } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-components';
import { useIntl } from '@umijs/max';
import { Alert, Card, Typography } from 'antd';
import React from 'react';
import {children} from "@umijs/utils/compiled/cheerio/lib/api/traversing";

const Admin: React.FC = (props) => {
  const intl = useIntl();
  const {children} = props;
  return (
    <PageContainer>
      {children}
    </PageContainer>
  );
};

export default Admin;
