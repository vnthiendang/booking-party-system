import React from "react";
import { Tag } from "antd";

export function UserResultDisplay(props) {
  const recentSearch = props.recentSearch;

  const checkRole = (role) => {
    switch (role) {
      case 'ADMIN':
        return <Tag color="red">Admin</Tag>;
      case 'HOST':
        return <Tag color="cyan">Host</Tag>;
      case "CUSTOMER":
        return <Tag color="pink">Customer</Tag>;
      default:
        return <></>;
    }
  };

  return (
    <div style={{ margin: "10px 0 0 0" }}>
      {recentSearch.name === null ? (
        ""
      ) : (
        <Tag color="orange">{recentSearch.name}</Tag>
      )}
      {recentSearch.role === null ? "" : checkRole(recentSearch.role)}

    </div>
  );
}
